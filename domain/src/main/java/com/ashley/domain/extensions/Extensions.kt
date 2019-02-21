package com.ashley.domain.extensions

import org.joda.time.DateTime

fun DateTime.isOutOfDate(): Boolean = this.isBefore(DateTime.now().minusDays(1))