package br.unisanta.tp07firebase.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tp.model.UserProfile
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class UsuarioViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _usuarioLogado = MutableLiveData<UserProfile?>()
    val usuarioLogado: LiveData<UserProfile?> = _usuarioLogado

    init {
        verificarUsuarioAtual()
    }

    fun verificarUsuarioAtual() {
        val user = auth.currentUser
        if (user != null) {
            _usuarioLogado.value = UserProfile(
                uid = user.uid,
                name = user.displayName ?: "Usuário Cadastrado",
                email = user.email ?: "Sem e-mail"
            )
        } else {
            _usuarioLogado.value = null
        }
    }

    fun deslogar(context: Context, onComplete: () -> Unit) {
        AuthUI.getInstance()
            .signOut(context)
            .addOnCompleteListener {
                _usuarioLogado.value = null
                onComplete()
            }
    }
}