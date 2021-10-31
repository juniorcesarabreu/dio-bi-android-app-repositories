package tk.juniorcesarabreu.apprepositories.core

class RemoteException(
    // override porque Throwable já tem construtor
    override val message: String
) : Throwable() {
}