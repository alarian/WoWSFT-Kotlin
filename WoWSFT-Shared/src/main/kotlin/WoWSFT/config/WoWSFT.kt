package WoWSFT.config

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@JacksonAnnotationsInside
@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY,
    setterVisibility = JsonAutoDetect.Visibility.NONE
)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
annotation class WoWSFT