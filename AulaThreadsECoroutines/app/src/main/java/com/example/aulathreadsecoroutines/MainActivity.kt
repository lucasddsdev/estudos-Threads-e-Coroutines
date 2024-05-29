package com.jamiltondamasceno.aulathreadscoroutines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.aulathreadsecoroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.lang.Runnable
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var pararThread = false
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnAbrir.setOnClickListener {
            startActivity(
                Intent(this, SegundaActivity::class.java)
            )
        }

        binding.btnParar.setOnClickListener {
            //pararThread = true
            job?.cancel()
            binding.btnIniciar.text = "Reiniciar execução"
            binding.btnIniciar.isEnabled = true
        }

        binding.btnIniciar.setOnClickListener {

            /*repeat(15){ indice ->
                Log.i("info_thread", "Executando: $indice T: ${Thread.currentThread().name}")
                Thread.sleep(1000)//ms 1000 -> 1s
            }*/
            //MinhaThread().start()
            //Thread( MinhaRunnable() ).start()
            /*Thread {
                repeat(30){ indice ->
                    Log.i("info_thread", "MinhaThread: $indice T: ${Thread.currentThread().name}")
                    runOnUiThread {
                        binding.btnIniciar.text = "Executando: $indice T: ${Thread.currentThread().name}"
                        binding.btnIniciar.isEnabled = false
                        if( indice == 29 ){
                            binding.btnIniciar.text = "Reiniciar execução"
                            binding.btnIniciar.isEnabled = true
                        }
                    }
                    Thread.sleep(1000)//ms 1000 -> 1s
                }
            }.start()*/

            job = CoroutineScope( Dispatchers.IO ).launch {
                /*repeat(15){ indice ->
                    Log.i("info_coroutine", "Executando: $indice T: ${Thread.currentThread().name}")

                    withContext( Dispatchers.Main ){
                        binding.btnIniciar.text = "Executando: $indice T: ${Thread.currentThread().name}"
                    }

                    delay(1000)//ms 1000 -> 1s
                }*/
                /*withTimeout(7000L){
                    executar()
                }*/

                val tempo = measureTimeMillis {

                    val resultado1 = async {tarefa1()}//pedro
                    val resultado2 = async {tarefa2()}//maria

                    withContext( Dispatchers.Main ){
                        binding.btnIniciar.text = "${resultado1.await()}"
                        binding.btnParar.text = "${resultado2.await()}"
                    }

                    Log.i("info_coroutine", "resultado1: ${resultado1.await()}")
                    Log.i("info_coroutine", "resultado2: ${resultado2.await()}")

                }
                Log.i("info_coroutine", "Tempo: $tempo")
            }


        }

    }

    private suspend fun tarefa1() : String {
        repeat(3){ indice ->
            Log.i("info_coroutine", "tarefa1: $indice T: ${Thread.currentThread().name}")
            delay(1000L)//ms 1000 -> 1s
        }
        return  "Executou tarefa 1"
    }

    private suspend fun tarefa2() : String {
        repeat(5){ indice ->
            Log.i("info_coroutine", "tarefa2: $indice T: ${Thread.currentThread().name}")
            delay(1000L)//ms 1000 -> 1s
        }
        return  "Executou tarefa 2"
    }

    private suspend fun executar(){
        repeat(15){ indice ->
            Log.i("info_coroutine", "Executando: $indice T: ${Thread.currentThread().name}")

            withContext( Dispatchers.Main ){
                binding.btnIniciar.text = "Executando: $indice T: ${Thread.currentThread().name}"
                binding.btnIniciar.isEnabled = false
            }

            delay(1000L)//ms 1000 -> 1s
        }
    }

    private suspend fun dadosUsuario(){
        val usuario = recuperarUsuarioLogado()
        Log.i("info_coroutine", "usuario: ${usuario.nome} T: ${Thread.currentThread().name}")
        val postagens = recuperarPostagensPeloId( usuario.id )
        Log.i("info_coroutine", "postagens: ${postagens.size} T: ${Thread.currentThread().name}")
    }

    private suspend fun recuperarPostagensPeloId( idUsuario: Int ) : List<String> {
        delay(2000)//2s
        return listOf(
            "Viagem Nordeste",
            "Estudando Android",
            "Jantando restaurante"
        )
    }

    private suspend fun recuperarUsuarioLogado(): Usuario {

        delay(2000)//2s
        return Usuario(1020, "Jamilton Damasceno")

    }

    inner class MinhaRunnable : Runnable {
        override fun run() {
            repeat(30){ indice ->

                if ( pararThread ){
                    pararThread = false
                    return
                }


                Log.i("info_thread", "MinhaThread: $indice T: ${Thread.currentThread().name}")
                runOnUiThread {
                    binding.btnIniciar.text = "Executando: $indice T: ${Thread.currentThread().name}"
                    binding.btnIniciar.isEnabled = false
                    if( indice == 29 ){
                        binding.btnIniciar.text = "Reiniciar execução"
                        binding.btnIniciar.isEnabled = true
                    }
                }
                Thread.sleep(1000)//ms 1000 -> 1s
            }
        }
    }

    inner class MinhaThread : Thread() {

        override fun run() {
            super.run()

            repeat(30){ indice ->
                Log.i("info_thread", "MinhaThread: $indice T: ${currentThread().name}")
                runOnUiThread {
                    binding.btnIniciar.text = "Executando: $indice T: ${currentThread().name}"
                    binding.btnIniciar.isEnabled = false
                    if( indice == 29 ){
                        binding.btnIniciar.text = "Reiniciar execução"
                        binding.btnIniciar.isEnabled = true
                    }
                }
                sleep(1000)//ms 1000 -> 1s
            }

        }

    }

}