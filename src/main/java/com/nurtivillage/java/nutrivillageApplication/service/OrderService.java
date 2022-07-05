package com.nurtivillage.java.nutrivillageApplication.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.nurtivillage.java.nutrivillageApplication.Request.OrderRequest;
import com.nurtivillage.java.nutrivillageApplication.dao.OfferRepository;
import com.nurtivillage.java.nutrivillageApplication.dao.OrderDetailsRepository;
import com.nurtivillage.java.nutrivillageApplication.dao.OrderRepository;
import com.nurtivillage.java.nutrivillageApplication.dao.UserProfileRepository;
import com.nurtivillage.java.nutrivillageApplication.dao.UserRepository;
import com.nurtivillage.java.nutrivillageApplication.dto.StatusRequest;
import com.nurtivillage.java.nutrivillageApplication.jwt.JwtTokenUtil;
import com.nurtivillage.java.nutrivillageApplication.model.Cart;
import com.nurtivillage.java.nutrivillageApplication.model.Inventory;
import com.nurtivillage.java.nutrivillageApplication.model.Offer;
import com.nurtivillage.java.nutrivillageApplication.model.OrderDetails;
import com.nurtivillage.java.nutrivillageApplication.model.Product;
import com.nurtivillage.java.nutrivillageApplication.model.ShippingAddress;
import com.nurtivillage.java.nutrivillageApplication.model.Status;
import com.nurtivillage.java.nutrivillageApplication.model.User;
import com.nurtivillage.java.nutrivillageApplication.model.UserOrder;
import com.nurtivillage.java.nutrivillageApplication.model.UserProfile;
import com.nurtivillage.java.nutrivillageApplication.model.Variant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
	private static final Logger log = LogManager.getLogger(OrderService.class);
	@Autowired
	public OrderRepository orderRepository;

	@Autowired
	public OrderDetailsRepository orderDetailsRepository;

	@Autowired
	public OrderService orderService;

	@Autowired
	public CartService cartService;

	@Autowired
	public InventoryService inventoryService;

	@Autowired
	private UserProfileRepository profileRepo;

	@Autowired
	public OfferService offerService;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${spring.mail.username}")
	private String fromMail;

	double totalAmount = 0;

	public List<UserOrder> getAllOrder() {
		List<UserOrder> userOrder = orderRepository.findByStatusNotOrderByCreatedAtAsc(Status.canceled);
		return userOrder;
	}

	public UserOrder createOrder(UserOrder order) {
		UserOrder orderCreate = orderRepository.save(order);
		return orderCreate;
	}

	public List<OrderDetails> createOrderDetails(List<Cart> cartItems, UserOrder order) {// (List<Product>
																							// product,UserOrder
																							// order,List<Long>
																							// quantity){
		List<OrderDetails> orderAllItem = new ArrayList<>();
		cartItems.forEach((var) -> {
			Cart cartItem = cartService.cartItemById(var.getId());
			Variant variant = cartItem.getInventory().getVariant();
			int price = cartItem.getInventory().getPrice();
			List<Offer> offer = offerService.getOffersByProduct(cartItem.getProduct().getId());
			if (offer.size() != 0) {
				OrderDetails orderItem = new OrderDetails(cartItem.getProduct(), order, cartItem.getQuantity(), variant,
						offer.get(0), price);
				orderAllItem.add(orderItem);
			} else {
				OrderDetails orderItem = new OrderDetails(cartItem.getProduct(), order, cartItem.getQuantity(), variant,
						null, price);
				orderAllItem.add(orderItem);
			}
		});
		orderDetailsRepository.saveAll(orderAllItem);
		cartService.cartClear();
		return orderAllItem;
	}

	public OrderDetails createSingleOrderDetails(Long productId, int variantId, int quantity, UserOrder order) {// (List<Product>
																												// product,UserOrder
																												// order,List<Long>
																												// quantity){
		OrderDetails orderItem = null;
		Inventory inventory = inventoryService.getProductVariantInventory(productId, variantId);
		List<Offer> offer = offerService.getOffersByProduct(inventory.getProduct().getId());
		if (offer.size() != 0) {
			orderItem = new OrderDetails(inventory.getProduct(), order, quantity, inventory.getVariant(), offer.get(0),
					inventory.getPrice());

		} else {
			orderItem = new OrderDetails(inventory.getProduct(), order, quantity, inventory.getVariant(), null,
					inventory.getPrice());

		}
		orderDetailsRepository.save(orderItem);

		return orderItem;
	}

	public OrderDetails getOrderDetail(Long id) throws Exception {
		try {
			Optional<OrderDetails> orderDetails = orderDetailsRepository.findById(id);
			if (orderDetails.isPresent()) {
				return orderDetails.get();
			}
			log.error("Order Details are not present with this id :" + id);
			throw new Exception("Order Details are not present with this id :" + id);
		} catch (Exception e) {
			throw e;
		}
	}

	public List<OrderDetails> findByUesrOrder(UserOrder order) {
		List<OrderDetails> orderDetails = orderDetailsRepository.findByUesrOrder(order);
		return orderDetails;
	}

	public UserOrder getOrder(Long id) throws Exception {
		try {
			Optional<UserOrder> order = orderRepository.findById(id);
			if (order.isPresent()) {
				return order.get();
			}
			throw new Exception("User order not present with this id: " + id);
		} catch (Exception e) {
			throw e;
		}
	}

	public UserOrder orderStatus(StatusRequest statusRequest) throws Exception {
		try {
			UserOrder orderInfo = orderService.getOrder(statusRequest.getId());

			Status updateStatus = getStatus(statusRequest.getStatus());
			orderInfo.setStatus(updateStatus);
			orderInfo.setComment(statusRequest.getComment());
			UserOrder updatedOrder = orderRepository.save(orderInfo);
			return updatedOrder;
		} catch (Exception e) {
			throw e;
		}

	}

	private Status getStatus(String status) {
		return Status.valueOf(status);
	}

	public List<?> getUserOrder(User user) {
		List<UserOrder> orderList = orderRepository.findByUser(user);
		List<UserOrder> paidOrderList = null;
		paidOrderList = orderList.stream().filter(o -> o.getPaymentStatus() != null || o.getPaymentMethod().equals("COD")).collect(Collectors.toList());
		return paidOrderList;
	}

	public Long getLastOrderNO() {
		List<UserOrder> userList = orderRepository.findAll();
		int Size = userList.size();
		if (Size == 0) {
			return (long) 0;
		}
		UserOrder userOrder = userList.get(Size - 1);
		Long init = (long) 1;
		return userOrder.getOrderNo() == null ? init : userOrder.getOrderNo();

	}

	public List<UserOrder> getAllCancelOrder() {
		List<UserOrder> userOrder = orderRepository.findByStatusOrderByCreatedAtAsc(Status.canceled);
		return userOrder;
	}

	public List<UserOrder> getUserCancelOrder(Long userId) {
		List<UserOrder> userOrder = orderRepository.findByStatusAndUserIdOrderByCreatedAtAsc(Status.canceled, userId);
		return userOrder;
	}

	public boolean amountVarify(double amount, List<Cart> cartItems) {
		totalAmount = 0;
		cartItems.forEach(cartinfo -> {
			System.out.println(cartinfo.getId());
			Cart cartItem = cartService.cartItemById(cartinfo.getId());
			double price = cartItem.getInventory().getPrice();
			price = price * cartItem.getQuantity();
			// get discount amount;
			Offer discount = null;
			List<Offer> productOffer = offerService.getOffersByProduct(cartItem.getInventory().getProduct().getId());
			if (productOffer.size() != 0) {
				discount = productOffer.get(0);
			}
//            List<Offer> categoryOffer = offerService.getOffersByCategory(cartItem.getInventory().getProduct().getCategory().getId());
//            if(categoryOffer.size() != 0 && discount == null){
//                discount = categoryOffer.get(0);
//            }
			totalAmount = totalAmount + price;
			if (discount != null) {
				int number = Integer.parseInt(discount.getAmount());
				
				if (discount.getDiscountType().equals("PERCENT")) {
					totalAmount = totalAmount - (totalAmount * number) / 100;
				} else {
					number = number * cartItem.getQuantity();
					totalAmount = totalAmount - number;
				}
			}
		});
		if (totalAmount != amount) {
			return false;
		}
		return true;
	}

	public boolean checkAmount(OrderRequest orderRequest) throws Exception {
		try {
			totalAmount = 0;
			Long productId = orderRequest.getProductId();
			int variantId = orderRequest.getVariantId();
			Inventory inventory = inventoryService.getProductVariantInventory(productId, variantId);
			if (inventory == null) {
				log.error("Product is not available");
				throw new Exception("Product is not available with id: " + productId);
			}
			double productPrice = inventory.getPrice();
			productPrice = productPrice * orderRequest.getQuantity();
			Offer discount = null;
			List<Offer> productOffer = offerService.getOffersByProduct(inventory.getProduct().getId());
			if (productOffer.size() != 0) {
				discount = productOffer.get(0);
			}
//             List<Offer> categoryOffer = offerService.getOffersByCategory(inventory.getProduct().getCategory().getId());
//             if(categoryOffer.size() != 0 && discount == null){
//                 discount = categoryOffer.get(0);
//             }
			totalAmount = totalAmount + productPrice;
			if (discount != null) {
				int number = Integer.parseInt(discount.getAmount());
				if (discount.getDiscountType().equals("PERCENT")) {
					totalAmount = totalAmount - (totalAmount * number) / 100;
				} else {
					number = number * orderRequest.getQuantity();
					totalAmount = totalAmount - number;
				}

			}
			if (totalAmount == orderRequest.getAmount()) {
				return true;
			} else {
				return false;
			}
		}

		catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	public boolean checkQuantity(Long productId, int variantId, int quantity) throws Exception {
		try {
			Inventory inventory = inventoryService.getProductVariantInventory(productId, variantId);
			if (inventory == null) {
				log.error("Product is not available with id: " + productId);
				throw new Exception("product is not available with id: " + productId);
			}
			if (inventory.getQuantity() >= quantity) {
				log.info("In Stock");
				return true;
			} else {
				log.error("Not in Stock");
				return false;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	// sending mail to owner
	public SimpleMailMessage sendMailToAdminForOrder(UserOrder order) {
		try {
			ShippingAddress address = order.getShippingAddress();
			final String subject = "Order received";
			final String message = "************ ORDER RECEIVED ************ \r\n # SHIPPING DETAILS # \r\n" + "Name : "
					+ address.getName() + "\r\n Country : " + address.getCountry() + "\r\n Street : "
					+ address.getStreet() + "\r\n State : " + address.getState() + "\r\n City : " + address.getCity()
					+ "\r\n Pincode : " + address.getPincode() + "\r\n Mobile : " + address.getMobile()
					+ "\r\n Email : " + order.getUser().getEmail() + "\r\n \r\n" + "# ORDER DETAILS # "
					+ "\r\n Order ID : " + order.getOrderNo() + "\r\n Order Amount : " + order.getAmount()
					+ "\r\n Order Status : " + order.getStatus().toString() + "\r\n Payment Method : "+ order.getPaymentMethod();
			final SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(fromMail);
			mail.setSubject(subject);
			mail.setFrom(fromMail);
			mail.setText(message);
			return mail;
		} catch (Exception e) {
			log.error("Error occured while sending mail to admin for order: " + e.getMessage());
			throw e;
		}
	}
	
	
	//sending mail to buyer
	public SimpleMailMessage sendMailToBuyerForOrder(UserOrder order) {
		try {
			ShippingAddress address = order.getShippingAddress();
			final String subject = "Order placed";
			final String message = "************ ORDER PLACED ************ \r\n \r\n Thank you for"
					+ " ordering from Nutri Village. \r\n Your order "+order.getOrderNo()+" has been placed.\r\n"
							+ " # SHIPPING DETAILS # \r\n" + "Name : "
					+ address.getName() + "\r\n Country : " + address.getCountry() + "\r\n Street : "
					+ address.getStreet() + "\r\n State : " + address.getState() + "\r\n City : " + address.getCity()
					+ "\r\n Pincode : " + address.getPincode() + "\r\n Mobile : " + address.getMobile()
					+ "\r\n Email : " + order.getUser().getEmail() + "\r\n \r\n" + "# ORDER DETAILS # "
					+ "\r\n Order ID : " + order.getOrderNo() + "\r\n Order Amount : " + order.getAmount()
					+ "\r\n Order Status : " + order.getStatus().toString() + "\r\n Payment Method : "+ order.getPaymentMethod();
			final SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(order.getUser().getEmail());
			mail.setSubject(subject);
			mail.setFrom(fromMail);
			mail.setText(message);
			return mail;
		} catch (Exception e) {
			log.error("Error occured while sending mail to admin for order: " + e.getMessage());
			throw e;
		}
	}

	// create guest user
	public User createGuestUser(ShippingAddress shippingAddress) {
		try {
			log.info("Creating User...");
			User user = new User();
			String[] name = shippingAddress.getName().split(" ");
			user.setFirstName(name[0]);
			user.setLastName(name[1]);
			user.setEmail(shippingAddress.getEmail());
			user.setPassword(name[1] + name[0]);
			user.setPhoneNo(shippingAddress.getMobile());
			user.setEnabled(true);
			UserProfile userProfile = new UserProfile();
			userProfile.setFirstName(name[0]);
			userProfile.setLastName(name[1]);
			userProfile.setAddress(shippingAddress.getStreet());
			userProfile.setCity(shippingAddress.getCity());
			userProfile.setCountry(shippingAddress.getCountry());
			userProfile.setEmail(shippingAddress.getEmail());
			userProfile.setPhone(shippingAddress.getMobile());
			user.setUserProfile(profileRepo.save(userProfile));
			log.info("User created with profile");
			return userRepository.save(user);
			
			

		} catch (Exception e) {
			throw e;
		}
	}
	public String createGuestToken(String email) {
		try {
			return jwtTokenUtil.createToken(email);
		}
		catch(Exception e) {
			throw e;
		}
	}
	public Map<String ,String> guestInfo(User user){
		try {
			 Map<String,String> guestInfo=new HashMap<>();
             guestInfo.put("guestId",user.getId().toString());
             guestInfo.put("guestEmail",user.getEmail());
             guestInfo.put("guestToken",createGuestToken(user.getEmail()));
             return guestInfo;
		}
		catch(Exception e) {
			throw e;
		}
	}

}
