package converter

import model.ThreadResult

fun Int.toThreadResult(primesAverage: Int, timeAverage: Int) = ThreadResult(
    threadsNumber = this,
    primesAverage = primesAverage,
    timeAverage = timeAverage
)