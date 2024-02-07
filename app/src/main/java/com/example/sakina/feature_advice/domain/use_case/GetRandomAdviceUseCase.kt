package com.example.sakina.feature_advice.domain.use_case

import kotlin.random.Random

class GetRandomAdviceIdUseCase() {

    operator fun invoke(count: Int): Int {
        return (1..count).random()
    }
}