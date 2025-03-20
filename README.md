# Kotlin WASM/WASI Benchmark

This project is a simple benchmark that computes prime numbers up to 100,000. It is written in Kotlin and compiled to a standalone WASM file using WASI.

## 🔧 Prerequisites

To run this benchmark, you need:

- Gradle (for building the project)
- WasmEdge (to execute the compiled WASM file)

## 🚀 Building and Running the Benchmark
#### 1. Build the WASM Binary

Run the following command to compile the project:
```
./gradlew wasmWasiWasmEdgeRun
```

#### 2. Run the Benchmark with WasmEdge

Once compiled, execute the WASM file using WasmEdge:
```
wasmedge --enable-function-reference --enable-gc --enable-exception-handling build/compileSync/wasmWasi/main/developmentExecutable/kotlin/kotlin-wasm-wasi-benchmark-wasm-wasi.wasm dummy
```
