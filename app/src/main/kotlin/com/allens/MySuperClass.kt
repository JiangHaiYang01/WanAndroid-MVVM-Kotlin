package com.allens

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class MySuperClass<T> protected constructor() {
    val type: Type = (javaClass.genericSuperclass as ParameterizedType)
        .actualTypeArguments[0]
}