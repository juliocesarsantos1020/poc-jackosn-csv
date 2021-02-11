package execute

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import model.Movimentacao
import java.io.FileReader


suspend fun main(args: Array<String>) {
    val movimentacoes: List<Movimentacao> =
        readCsvFile("/home/julio.santos/projetos/workspace/poc-jackson-csv/src/main/resources/inf_diario_fi_201911.csv")
    val total = movimentacoes.size
    movimentacoes.forEach {
        println(it.toString())
    }
    
    println("Total de linhas: $total")
}

val csvMapper: CsvMapper = CsvMapper().apply {
    registerModules(KotlinModule(), JavaTimeModule())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
}

suspend inline fun <reified T> readCsvFile(fileName: String): List<T> {
    FileReader(fileName).use { reader ->
        return csvMapper
            .readerFor(T::class.java)
            .with(CsvSchema.emptySchema().withHeader())
            .readValues<T>(reader)
            .readAll()
            .pmap { t -> t }
    }
}


suspend fun <A, B> Iterable<A>.pmap(f: suspend (A) -> B): List<B> = coroutineScope {
    map { async { f(it) } }.awaitAll()
}