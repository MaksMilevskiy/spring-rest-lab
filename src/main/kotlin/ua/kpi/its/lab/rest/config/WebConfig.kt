package ua.kpi.its.lab.rest.config

import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.function.*
import ua.kpi.its.lab.rest.dto.MobilePhoneRequest
import ua.kpi.its.lab.rest.svc.MobilePhoneService
import java.text.SimpleDateFormat


@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {
    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        val builder = Jackson2ObjectMapperBuilder()
            .indentOutput(true)
            .dateFormat(SimpleDateFormat("yyyy-MM-dd"))
            .modulesToInstall(KotlinModule.Builder().build())
        converters.add(MappingJackson2HttpMessageConverter(builder.build()))
    }

    @Bean
    fun functionalRoutes(mobilePhoneService: MobilePhoneService): RouterFunction<*> = router {
        "/fn".nest {
            "/phones".nest {
                GET("") {
                    try {
                        val phones = mobilePhoneService.read()
                        ok().body(phones)
                    } catch (e: IllegalArgumentException) {
                        notFound().build()
                    }
                }

                GET("/{id}") { req ->
                    val id = req.pathVariable("id").toLong()
                    try {
                        val phone = mobilePhoneService.readById(id)
                        ok().body(phone)
                    } catch (e: IllegalArgumentException) {
                        notFound().build()
                    }
                }

                POST("") { req ->
                    val phone = req.body<MobilePhoneRequest>()
                    try {
                        val createdPhone = mobilePhoneService.create(phone)
                        ok().body(createdPhone)
                    } catch (e: IllegalArgumentException) {
                        notFound().build()
                    }
                }

                PUT("/{id}") { req ->
                    val id = req.pathVariable("id").toLong()
                    val phone = req.body<MobilePhoneRequest>()
                    try {
                        val updatedPhone = mobilePhoneService.updateById(id, phone)
                        ok().body(updatedPhone)
                    } catch (e: IllegalArgumentException) {
                        notFound().build()
                    }
                }

                DELETE("/{id}") { req ->
                    val id = req.pathVariable("id").toLong()
                    try {
                        val deletedPhone = mobilePhoneService.deleteById(id)
                        ok().body(deletedPhone)
                    } catch (e: IllegalArgumentException) {
                        notFound().build()
                    }
                }
            }
        }
    }
}