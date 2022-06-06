package com.sreenath.bookstore.service.emailservice;

import com.sreenath.bookstore.dto.ResponseDTO;
import com.sreenath.bookstore.model.EmailData;
import org.springframework.http.ResponseEntity;

public interface IEmailService {
    ResponseEntity<ResponseDTO> sendEmail(EmailData emailData);

    String getLink(String token);

    String getOrderLink(String token);
}
