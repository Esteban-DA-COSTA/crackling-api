package com.crackling.domain.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

fun now() = Clock.System.todayIn(TimeZone.currentSystemDefault())