package com.example.footballsimulator.common.util

import kotlin.math.E
import kotlin.math.pow

fun Double.calculateMassFunction(numberOfOccurrences: Int) =
    ((this.pow(numberOfOccurrences) * E.pow(-this)) / (factorial(numberOfOccurrences))) * 100

private fun factorial(n: Int) = (2..n).fold(1L, Long::times)
