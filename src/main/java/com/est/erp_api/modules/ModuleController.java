package com.est.erp_api.modules;

import com.est.erp_api.configurations.AuthConverter;
import com.est.erp_api.modules.models.ModuleDetail;
import com.est.erp_api.modules.models.ModuleRoleDetail;
import com.est.erp_api.services.JWTService;
import com.est.erp_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @GetMapping
    public List<ModuleDetail> getAllModules() {
        return moduleService.getAllModules();
    }

    @GetMapping("/role/{role}")
    public List<ModuleDetail> getModulesByRole(@PathVariable("role") String role) {
        return moduleService.getModulesByRole(role);
    }

    @GetMapping("/user/{userId}")
    public Flux<ModuleDetail> getModulesByUser(@PathVariable("userId") String userId) {
        return moduleService.getModulesByUser(userId);
    }

    @GetMapping("/current")
    public Flux<ModuleRoleDetail> getModulesByCurrentUser(ServerWebExchange exchange) {
        return extractUserIdFromToken(exchange)
                .flatMapMany(moduleService::getModulesDetailsByUser);
    }


    private Mono<String> extractUserIdFromToken(ServerWebExchange exchange) {
        return new AuthConverter().convert(exchange)
                .flatMap(token -> {
                    try {
                        return Mono.just(jwtService.parse(token.getPrincipal().toString()));
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }

}
