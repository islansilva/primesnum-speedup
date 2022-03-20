package converter

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader

fun BufferedReader.toNumberList(): Set<Int> {
    return try {
        val csvParser = CSVParser(this, CSVFormat.DEFAULT)

        csvParser.map { it.get(0).toInt() }.toSet()
    } catch (e: Exception) {
        setOf()
    }
}