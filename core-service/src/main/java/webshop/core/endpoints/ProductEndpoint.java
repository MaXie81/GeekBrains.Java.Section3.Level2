package webshop.core.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import webshop.core.exceptions.ResourceNotFoundException;
import webshop.core.services.ProductService;
import webshop.core.soap.product.GetAllProductsRequest;
import webshop.core.soap.product.GetAllProductsResponse;
import webshop.core.soap.product.GetProductByIdRequest;
import webshop.core.soap.product.GetProductByIdResponse;
import webshop.core.soap.utils.Mapping;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.gb.com/spring/ws/product";
    private final ProductService productService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    public GetProductByIdResponse getStudentByName(@RequestPayload GetProductByIdRequest request) {
        GetProductByIdResponse response = new GetProductByIdResponse();
        response.setProduct(Mapping.getSoapProductFromProduct(productService
                .findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Продукт не найден, id: " + request.getId()))
        ));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllStudents(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        productService.findAll().stream()
                .map(Mapping::getSoapProductFromProduct)
                .forEach(response.getProducts()::add);
        return response;
    }
}

    /*
        Пример запроса: POST http://localhost:8189/ws
        Header -> Content-Type: text/xml

        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:f="http://www.gb.com/spring/ws/product">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getProductByIdRequest>
                    <f:id>1</f:id>
                </f:getProductByIdRequest>
            </soapenv:Body>
        </soapenv:Envelope>

        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:f="http://www.gb.com/spring/ws/product">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getAllProductsRequest/>
            </soapenv:Body>
        </soapenv:Envelope>
     */
