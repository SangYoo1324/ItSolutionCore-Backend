package com.example.ItSolutionCore.common.paypal;


import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PaypalController {


    private final PaypalService paypalService;


    @GetMapping("/")
    public String home(){
        return "index";
    }

    @PostMapping("/payment/create")
    public RedirectView createPayment(){

        try{
            String cancelUrl = "http://localhost:8080/payment/cancel";
            String successUrl = "http://localhost:8080/payment/success";

            Payment payment = paypalService.createPayment(
                10.0,
                    "USD",
                     "paypal",
            "sale",
                    "payment Description",
                    cancelUrl,
                    successUrl
            );

            for(Links link: payment.getLinks()){
                if(link.getRel().equals("approval_url")){
                    return new RedirectView(link.getHref());
                }
            }

        }catch(PayPalRESTException e){
            log.error("Error occurred::", e);
        }
        return new RedirectView("/payment/error");
    }

    @GetMapping("/payment/success")
    public String paymentSuccess(@RequestParam("paymentId") String paymentId,
                                 @RequestParam("PayerID")String payerId){


        try {
            Payment payment = paypalService.executePayment(paymentId,payerId);

            if(payment.getState().equals("approved")){
                return "paymentSuccess";
            }
        } catch (PayPalRESTException e) {
            log.error("Error occurred::", e);
        }


        return "paymentSuccess";
    }

    @GetMapping("/payment/cancel")
    public String paymentCancel() {
        return "paymentCancel";
    }

    @GetMapping("/payment/error")
    public String paymentError() {
        return "paymentError";
    }
}
