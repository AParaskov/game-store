package com.tusofia.apigateway.controller;

import com.tusofia.apigateway.dto.ProductAddRequest;
import com.tusofia.apigateway.dto.ProductDTO;
import com.tusofia.apigateway.dto.ProductUpdateRequest;
import com.tusofia.apigateway.dto.ShoppingCartProductAddRequest;
import com.tusofia.apigateway.service.ProductServiceConnector;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceConnector productServiceConnector;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ModelAndView products(ModelAndView modelAndView) {
        modelAndView.addObject("products", productServiceConnector.getAllProducts());
        modelAndView.setViewName("products");

        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    public ModelAndView add(@Valid @ModelAttribute("productAddRequest")
                          ProductAddRequest productAddRequest,
                      BindingResult bindingResult, ModelAndView modelAndView){

        modelAndView.addObject("productAddRequest", productAddRequest);
        modelAndView.addObject("category", productServiceConnector.getAllCategoryNames());
        modelAndView.setViewName("add-product");
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("productAddRequest") ProductAddRequest productAddRequest,
                             @RequestParam("category")String category,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productAddRequest", productAddRequest);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productAddRequest", bindingResult);
            return "redirect:add";
        } else {
           productServiceConnector.addProduct(productAddRequest, category);
           return "redirect:/home";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable("id")String id, ModelAndView modelAndView){
        ProductDTO product = productServiceConnector.getProductById(id);
        ShoppingCartProductAddRequest shoppingCartProductAdd = new ShoppingCartProductAddRequest();
        shoppingCartProductAdd.setProductId(product.getId());

        modelAndView.addObject("product", product);
        //modelAndView.addObject("reviews", productServiceConnector.findReviewsByProductId(id));
        modelAndView.addObject("shoppingCartProductAdd", shoppingCartProductAdd);
        modelAndView.setViewName("details");
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")String id){
        productServiceConnector.deleteProduct(id);
        return "redirect:/admin";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/update")
    public ModelAndView update(@Valid @ModelAttribute("productUpdateRequest")
                                   ProductUpdateRequest productUpdateRequest,
                               BindingResult bindingResult, ModelAndView modelAndView){

        modelAndView.addObject("productUpdateRequest", productUpdateRequest);
        modelAndView.setViewName("update-product");
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public String updateConfirm(@Valid @ModelAttribute("productUpdateRequest") ProductUpdateRequest productUpdateRequest,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productUpdateRequest", productUpdateRequest);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productUpdateRequest", bindingResult);
            return "redirect:update";
        } else {
            productServiceConnector.updateProduct(productUpdateRequest);
            return "redirect:/home";
        }
    }
}
