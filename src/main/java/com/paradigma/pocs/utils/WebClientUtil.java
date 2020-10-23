package com.paradigma.pocs.utils;

import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

public class WebClientUtil {

    public static ExchangeFilterFunction errorHandlingFilterOauth() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if(clientResponse.statusCode()!=null && (clientResponse.statusCode().is5xxServerError() || clientResponse.statusCode().is4xxClientError()) ) {
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> {
                            return Mono.error(new Exception("Error"));
                        });
            }else {
                return Mono.just(clientResponse);
            }
        });
    }
}
