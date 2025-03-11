import kotlin.wasm.WasmImport
import kotlin.wasm.unsafe.Pointer
import kotlin.wasm.unsafe.UnsafeWasmMemoryApi
import kotlin.wasm.unsafe.withScopedMemoryAllocator

fun main(args: Array<String>) {
    val defaultThreshold = 100_000
    val threshold = args.getOrNull(0)?.toIntOrNull() ?: defaultThreshold
    println("Benchmarking prime computation up to $threshold")

    val start = wasiMonotonicTime()
    val primes = computePrimes(threshold)
    val end = wasiMonotonicTime()

    val elapsedTimeSeconds = (end - start) / 1_000_000_000.0

    println("Found ${primes.size} prime numbers up to $threshold")
    println("Execution time: $elapsedTimeSeconds seconds")
}

fun computePrimes(limit: Int): List<Int> {
    val primes = mutableListOf<Int>()
    for (i in 2..limit) {
        if (primes.none { i % it == 0 }) {
            primes.add(i)
        }
    }
    return primes
}

@WasmImport("wasi_snapshot_preview1", "clock_time_get")
private external fun wasiRawClockTimeGet(clockId: Int, precision: Long, resultPtr: Int): Int

private const val REALTIME = 0
private const val MONOTONIC = 1

@OptIn(UnsafeWasmMemoryApi::class)
fun wasiGetTime(clockId: Int): Long = withScopedMemoryAllocator { allocator ->
    val rp0 = allocator.allocate(8)
    val ret = wasiRawClockTimeGet(
        clockId = clockId,
        precision = 1,
        resultPtr = rp0.address.toInt()
    )
    check(ret == 0) {
        "Invalid WASI return code $ret"
    }
    (Pointer(rp0.address.toInt().toUInt())).loadLong()
}

fun wasiRealTime(): Long = wasiGetTime(REALTIME)

fun wasiMonotonicTime(): Long = wasiGetTime(MONOTONIC)

// We need it to run WasmEdge with the _initialize function
@WasmExport
fun dummy() {}
