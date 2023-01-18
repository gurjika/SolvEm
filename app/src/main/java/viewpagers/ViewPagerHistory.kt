package viewpagers

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.civa.fragment.*

class ViewPagerHistory(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                FragmentHistory()
            }
            1 -> {
                FragmentHistoryVector()
            }
            else -> {
                FragmentHistoryBinary()
            }
        }
    }
}
