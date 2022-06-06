package com.sreenath.bookstore.controller;

import com.sreenath.bookstore.dto.CartDTO;
import com.sreenath.bookstore.dto.ResponseDTO;
import com.sreenath.bookstore.model.CartData;
import com.sreenath.bookstore.service.cartservice.ICartService;
import com.sreenath.bookstore.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartRestController {
    @Autowired
    private ICartService iCartService;

    @Autowired
    private TokenUtil tokenUtil;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<ResponseDTO> findAllCarts() {
        Iterable<CartData> allCarts = iCartService.findAllCarts();
        ResponseDTO responseDTO = new ResponseDTO("All items in Carts", allCarts);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get_by_id/{token}")
    public ResponseEntity<ResponseDTO> getCartById(@PathVariable("token") String token) {
        int tokenId = tokenUtil.decodeToken(token);
        CartData cartData = iCartService.getCartById(tokenId);
        ResponseDTO responseDTO = new ResponseDTO("Get Cart call Success for Id", cartData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/add_cart")
    public ResponseEntity<ResponseDTO> addToCart(@RequestBody CartDTO cartDTO) {
        CartData cartData = iCartService.addToCart(cartDTO);
        String token = tokenUtil.createToken(cartData.getCartId());
        ResponseDTO responseDTO = new ResponseDTO("Product Added To Cart", cartData, token);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update_quantity/{token}")
    public ResponseEntity<ResponseDTO> updateBookQuantity(@PathVariable("token") String token,
                                                          @RequestParam(value = "quantity") int quantity) {
        int tokenId = tokenUtil.decodeToken(token);
        CartData cartData = iCartService.updateQuantity(tokenId, quantity);
        ResponseDTO responseDTO = new ResponseDTO("Updated quantity call success for Id", cartData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete_cart/{token}")
    public ResponseEntity<ResponseDTO> deleteCart(@PathVariable("token") String token) {
        int tokenId = tokenUtil.decodeToken(token);
        iCartService.deleteCart(tokenId);
        ResponseDTO responseDTO = new ResponseDTO("Delete call success for Id", "Deleted cart id : " + tokenId);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
