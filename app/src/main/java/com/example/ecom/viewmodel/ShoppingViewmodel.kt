import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecom.data.product
import com.example.ecom.util.Resource
import com.example.ecom.viewmodel.FirebaseDb

class ShoppingViewModel(
    private val firebaseDatabase: FirebaseDb
) : ViewModel() {

    val search = MutableLiveData<Resource<List<product>>>()

    fun searchProducts(searchQuery: String) {
        search.postValue(Resource.Loading())
        firebaseDatabase.searchProducts(searchQuery).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val productsList = task.result?.toObjects(product::class.java) ?: emptyList()
                search.postValue(Resource.Success(productsList))
            } else {
                search.postValue(Resource.Error(task.exception?.toString() ?: "Unknown error"))
            }
        }
    }
}
