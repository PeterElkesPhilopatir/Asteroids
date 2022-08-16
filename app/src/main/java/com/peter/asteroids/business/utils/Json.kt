package com.peter.asteroids.business.utils

import com.google.gson.Gson


fun <A> String.fromJson(type: Class<A>): A =
    Gson().fromJson(this, type)

fun <A> A.toJson(): String = Gson().toJson(this)