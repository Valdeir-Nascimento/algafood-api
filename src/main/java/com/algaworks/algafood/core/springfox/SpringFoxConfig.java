package com.algaworks.algafood.core.springfox;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.controller.swagger.PageableDTOSwagger;
import com.algaworks.algafood.api.v1.dto.*;
import com.algaworks.algafood.api.v1.dto.swagger.*;
import com.algaworks.algafood.api.v2.controller.swagger.CidadesDTOV2Swagger;
import com.algaworks.algafood.api.v2.controller.swagger.CozinhasDTOV2Swagger;
import com.algaworks.algafood.api.v2.dto.CidadeDTOV2;
import com.algaworks.algafood.api.v2.dto.CozinhaDTOV2;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

    @Bean
    public Docket apiDocketV1() {
        TypeResolver typeResolver = new TypeResolver();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("V1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
                .paths(PathSelectors.ant("/v1/**"))
                .build()
                .securitySchemes(Arrays.asList(securityScheme()))
                .securityContexts(Arrays.asList(securityContext()))
                .apiInfo(apiInfoV1())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalGetResponseMessage())
                .globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessage())
                .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessage())
                .directModelSubstitute(Pageable.class, PageableDTOSwagger.class)
                .directModelSubstitute(Links.class, LinksDTOSwagger.class)
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(PagedModel.class, CozinhaDTO.class),
                        CozinhasDTOSwagger.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(Page.class, PedidoResumoDTO.class),
                        PedidoResumoDTOSwagger.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, CidadeDTO.class),
                        CidadesDTOSwagger.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, EstadoDTO.class),
                        EstadosDTOSwagger.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, FormaPagamentoDTO.class),
                        FormasPagamentoDTOSwagger.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, GrupoDTO.class),
                        GruposDTOSwagger.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, PermissaoDTO.class),
                        PermissoesDTOSwagger.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, PedidoResumoDTO.class),
                        PedidosResumoDTOSwagger.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, ProdutoDTO.class),
                        ProdutosDTOSwagger.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, RestauranteBasicoDTO.class),
                        RestaurantesBasicoDTOSwagger.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, UsuarioDTO.class),
                        UsuariosDTOSwagger.class))

                .additionalModels(typeResolver.resolve(Problem.class))
                .ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class, Resource.class, File.class, InputStream.class)
                .tags(new Tag("Cidades", "Gerencia as cidades"),
                        new Tag("Grupos", "Gerencia os grupos de usuários"),
                        new Tag("Cozinhas", "Gerencia as cozinhas"),
                        new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
                        new Tag("Pedidos", "Gerencia os pedidos"),
                        new Tag("Restaurantes", "Gerencia os restaurantes"),
                        new Tag("Estados", "Gerencia os estados"),
                        new Tag("Produtos", "Gerencia os produtos de restaurantes"),
                        new Tag("Usuários", "Gerencia os usuários"),
                        new Tag("Estatísticas", "Estatísticas da AlgaFood"),
                        new Tag("Permissões", "Gerencia as permissões"));
    }

    @Bean
    public Docket apiDocketV2() {
        TypeResolver typeResolver = new TypeResolver();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("V2")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
                .paths(PathSelectors.ant("/v2/**"))
                .build()
                .apiInfo(apiInfoV2())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalGetResponseMessage())
                .globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessage())
                .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessage())
                .directModelSubstitute(Pageable.class, PageableDTOSwagger.class)
                .directModelSubstitute(Links.class, LinksDTOSwagger.class)
                .additionalModels(typeResolver.resolve(Problem.class))
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, CozinhaDTOV2.class),
                        CozinhasDTOV2Swagger.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, CidadeDTOV2.class),
                        CidadesDTOV2Swagger.class))
                .ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class, Resource.class, File.class, InputStream.class);

    }

    private List<ResponseMessage> globalGetResponseMessage() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Erro interno do servidor")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build()
        );
    }

    private List<ResponseMessage> globalPostPutResponseMessage() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Requisição inválida (erro do cliente)")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Erro interno do servidor")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                        .message("Requisição recusada porque o corpo está em um formato não suportado")
                        .responseModel(new ModelRef("Problema"))
                        .build()
        );
    }

    private List<ResponseMessage> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Requisição inválida (erro do cliente)")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Erro interno no servidor")
                        .responseModel(new ModelRef("Problema"))
                        .build()
        );
    }

    private ApiInfo apiInfoV1() {
        return new ApiInfoBuilder()
                .title("Algafood API")
                .description("API aberta para clientes e restaurantes.<br>" +
                        "  <strong>Essa versão da API está depreciada é deixará de existir 10/10/2022." +
                        " Use a versão mais atual da API.</strong>")
                .version("1.0.0")
                .contact(new Contact("Algaworks", "https://www.algaworks.com", "contato@algaworks"))
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private ApiInfo apiInfoV2() {
        return new ApiInfoBuilder()
                .title("Algafood API")
                .description("API aberta para clientes e restaurantes")
                .version("2.0.0")
                .contact(new Contact("Algaworks", "https://www.algaworks.com", "contato@algaworks"))
                .build();
    }

    private SecurityScheme securityScheme() {
        return new OAuthBuilder()
                .name("Algafood")
                .grantTypes(grantTypes())
                .scopes(scopes())
                .build();
    }

    private SecurityContext securityContext() {
        var securityReference = SecurityReference.builder()
                .reference("Algafood")
                .scopes(scopes().toArray(new AuthorizationScope[0])) //Convertendo para um array
                .build();

        return SecurityContext.builder()
                .securityReferences(Arrays.asList(securityReference))
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<AuthorizationScope> scopes() {
        return Arrays.asList(
                new AuthorizationScope("READ", "Acesso de leitura"),
                new AuthorizationScope("WRITE", "Acesso de escrita"));
    }

    private List<GrantType> grantTypes() {
        return Arrays.asList(new ResourceOwnerPasswordCredentialsGrant("/oauth/token"));
    }

}
