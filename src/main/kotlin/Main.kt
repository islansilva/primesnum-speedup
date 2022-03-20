import common.ExportSVG
import common.PlotUtils.createFile
import common.PlotUtils.createPlot
import common.PlotUtils.openInBrowser
import converter.toNumberList
import converter.toPlotData
import solver.PrimeSolverMultiThreaded
import solver.PrimeSolverSingleThreaded
import java.nio.file.Files
import java.nio.file.Paths

const val CSV_FILE_PATH = "src/main/resources/data.csv"
const val NUMBER_OF_INITIAL_THREADS = 2
const val NUMBER_OF_FINAL_THREADS = 20
const val NUMBER_OF_SAMPLES = 50

fun main() {

    val simpleSolver = PrimeSolverSingleThreaded()
    val threadSolverMultiThreaded = PrimeSolverMultiThreaded()

    val bufferedReader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH))

    val numbers = bufferedReader.toNumberList()

    println("Iniciando cálculos por abordagem simples...")
    println("Número de amostras: $NUMBER_OF_SAMPLES")

    var simpleSolverTimeAmount = 0

    for (i in 1..NUMBER_OF_SAMPLES){
        val conventionalResult = simpleSolver.checkPrimeNumbers(numbers)
        simpleSolverTimeAmount += conventionalResult.time
    }

    val simpleSolverAverageTime = simpleSolverTimeAmount/NUMBER_OF_SAMPLES

    println("Média da abordagem tradicional: $simpleSolverAverageTime ms\n")

    val threadsNumber = mutableListOf<Int>()
    val threadsTimeAverage = mutableListOf<Int>()

    println("Iníciando cálculos com threads...\n")

    for (i in NUMBER_OF_INITIAL_THREADS..NUMBER_OF_FINAL_THREADS){

        var primesAmount = 0
        var threadSolverTimeAmount = 0

        for (j in 1..NUMBER_OF_SAMPLES){
            val threadsResult = threadSolverMultiThreaded.checkPrimeNumbers(numbers, i)
            primesAmount += threadsResult.primes
            threadSolverTimeAmount += threadsResult.time
        }

        threadsNumber.add(i)

        val threadAverageTime = threadSolverTimeAmount / NUMBER_OF_SAMPLES
        val primesAverage = primesAmount / NUMBER_OF_SAMPLES

        threadsTimeAverage.add(threadAverageTime)

        println("Thread $i")
        println("Média de números primos: $primesAverage")
        println("Média de tempo das threads com $NUMBER_OF_SAMPLES amostras: $threadAverageTime ms\n")
    }

    println("Finalizado cálculos com threads.")

    val speedUps: List<Float> = threadsTimeAverage.map { (simpleSolverTimeAmount/NUMBER_OF_SAMPLES) / it.toFloat()  }

    val threadsData = mapOf<String, Any>("Número de threads" to threadsNumber, "Tempo (ms)" to threadsTimeAverage)
        .toPlotData("Tempo para execução da busca por número de Threads", "Número de threads", "Tempo (ms)")

    val speedUpData = mapOf<String, Any>("Número de threads" to threadsNumber, "SpeedUp" to speedUps)
        .toPlotData("SpeedUp por número de Threads", "Número de threads", "SpeedUp")

    val threadsPlot = createPlot(threadsData)
    val speedUpPlot = createPlot(speedUpData)


    val plotContent = ExportSVG.buildSvgFromList(listOf(threadsPlot, speedUpPlot))

    val plotFile = createFile(plotContent)

    openInBrowser(plotFile)
}