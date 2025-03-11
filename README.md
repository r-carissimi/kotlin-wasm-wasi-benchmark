# Kotlin WASM/WASI Benchmark

This is a simple benchmark that computes the prime numbers up to 100000. 
It's written in Kotlin and can be compiled to a standalone WASM file using WASI.

To run the benchmark just use Gradle `./gradlew wasmWasiWasmEdgeRun`.

The standalone WASM file can be executed with WasmEdge.

`wasmedge --enable-function-reference --enable-gc --enable-exception-handling build/compileSync/wasmWasi/main/developmentExecutable/kotlin/kotlin-wasm-wasi-benchmark-wasm-wasi.wasm dummy`

Soon or later I'll make this readme better!