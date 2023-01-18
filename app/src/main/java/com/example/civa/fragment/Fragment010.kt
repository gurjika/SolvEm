package navigation

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.civa.R

class Fragment010:Fragment(R.layout.fragment_010) {
    private lateinit var buttonGoToNtoBinary:Button
    private lateinit var buttonGoToBinaryToN:Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonGoToBinaryToN = view.findViewById(R.id.buttonGoToBinaryToN)
        buttonGoToNtoBinary = view.findViewById(R.id.buttonGoToNToBinary)


        buttonGoToBinaryToN.setOnClickListener {
            val action = Fragment010Directions
                .actionFragment010ToFragmentBinaryToN(null)
            findNavController().navigate(action)
        }
        buttonGoToNtoBinary.setOnClickListener {
            val action = Fragment010Directions
                .actionFragment010ToFragmentNToBinary(null)
            findNavController().navigate(action)
        }
    }
}