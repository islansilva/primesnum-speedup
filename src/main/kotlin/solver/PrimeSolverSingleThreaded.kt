package solver

import model.PrimeCount

class PrimeSolverSingleThreaded {

    fun checkPrimeNumbers(numbers: Set<Int>): PrimeCount {
        val begin = System.currentTimeMillis()

        val primesCount = numbers.count { isPrimeNumber(it) }

        val end = System.currentTimeMillis()

        return primesCount.toPrimesCountObject(end - begin)
    }

    private fun isPrimeNumber(value: Int): Boolean {

        var divisorNumber = 2

        while (divisorNumber * divisorNumber <= value) {

            if (value % divisorNumber == 0) return false

            divisorNumber++
        }

        return true
    }

    private fun Int.toPrimesCountObject(time: Long) = PrimeCount(
        primes = this,
        time = time.toInt()
    )
}