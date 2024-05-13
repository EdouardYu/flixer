package web.technologies.flixer.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import web.technologies.flixer.entity.Validation;

@AllArgsConstructor
@Service
public class NotificationService {
    private final JavaMailSender javaMailSender;

    public void sendActivationCodeEmail(Validation validation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@flixer.com");
        message.setTo(validation.getUser().getEmail());
        message.setSubject("Flixer activation code");

        String text = "Here's the activation code to create your Flixer account\n"
            + validation.getCode()
            + "\nThis code is only valid for 10 minutes";
        message.setText(text);

        this.javaMailSender.send(message);
    }

    public void sendPasswordResetEmail(Validation validation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@flixer.com");
        message.setTo(validation.getUser().getEmail());
        message.setSubject("Flixer password reset code");

        String text = "Here's the code to reset your Flixer account password\n"
            + validation.getCode()
            + "\nThis code is only valid for 10 minutes";
        message.setText(text);

        this.javaMailSender.send(message);
    }
}
