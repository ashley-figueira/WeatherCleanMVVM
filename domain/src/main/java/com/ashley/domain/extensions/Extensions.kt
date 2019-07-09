package com.ashley.domain.extensions

import com.ashley.domain.common.WError
import com.ashley.domain.common.WResult
import org.joda.time.DateTime

fun <T> T.toResult(): WResult<T> = WResult.Success(this)

fun <T> WError.toResult(): WResult<T> = WResult.Failure(this)

fun DateTime.isOutOfDate(): Boolean = this.isBefore(DateTime.now().minusDays(1))