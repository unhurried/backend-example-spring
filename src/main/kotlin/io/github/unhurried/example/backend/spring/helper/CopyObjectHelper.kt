package io.github.unhurried.example.backend.spring.helper

import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component
import java.util.*

/** A helper class that makes creating and copying objects easy.  */
@Component
class CopyObjectHelper {
    fun <S: Any, T: Any> createAndCopyIterable(src: Iterable<S>, clazz: Class<T>): Iterable<T> {
        return createAndCopyIterable(src, clazz, null)
    }

    fun <S: Any, T: Any> createAndCopyIterable(src: Iterable<S>, clazz: Class<T>, function: ((S, T) -> Unit)?): Iterable<T> {
        val target: MutableList<T> = ArrayList()
        for (s in src) {
            target.add(createAndCopy(s, clazz, function))
        }
        return target
    }

    fun <T: Any> createAndCopy(src: Any, clazz: Class<T>): T {
        return createAndCopy(src, clazz, null)
    }

    fun <S: Any, T: Any> createAndCopy(src: S, clazz: Class<T>, function: ((S, T) -> Unit)?): T {
        val target = BeanUtils.instantiateClass(clazz)
        BeanUtils.copyProperties(src, target)
        function?.invoke(src, target)
        return target
    }

    fun copyProperties(source: Any, target: Any) {
        BeanUtils.copyProperties(source, target)
    }
}