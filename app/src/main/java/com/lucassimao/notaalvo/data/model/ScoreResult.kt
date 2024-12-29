package com.lucassimao.notaalvo.data.model

import com.lucassimao.notaalvo.util.ScoreStatus

data class ScoreResult(
    val titleResId: Int? = null,
    val messageResId: Int? = null,
    val status: ScoreStatus? = null,
    val examScore: Double? = null
)