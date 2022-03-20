package solver

import model.PrimeCount

class PrimeSolverMultiThreaded {

    companion object{
        private val threads: MutableList<Thread> = mutableListOf()
    }

    fun checkPrimeNumbers(numbers: Set<Int>, threadsNumber: Int): PrimeCount {

        val begin = System.currentTimeMillis()

        var primesCount = 0

        val splitNumberList = splitNumberList(numbers, threadsNumber)

        splitNumberList.forEach { it ->
            val thread = Thread {
                val primes = it.count { isPrimeNumber(it) }
                primesCount += primes
            }
            thread.start()
            threads.add(thread)
        }

        threads.forEach{
            it.join()
        }

        val end = System.currentTimeMillis()

        return primesCount.toPrimesCountObject(end - begin)
    }

    private fun splitNumberList(numbers: Set<Int>, threadsNumber: Int): List<List<Int>>{
        if (numbers.size % threadsNumber == 0)
            return numbers.chunked(numbers.size / threadsNumber)

        return numbers.chunked(numbers.size / threadsNumber + 1)
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