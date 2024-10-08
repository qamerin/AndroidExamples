import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.qamerin.mycampapp.R
import com.qamerin.mycampapp.model.GearCategoryModel

class GearCategoryAdapter(
    private val context: Context,
    private val gearCategories: List<GearCategoryModel>,
    private var selectedCategoryId: Long // 選択されたカテゴリIDを受け取る
) : RecyclerView.Adapter<GearCategoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val radioButton: RadioButton = view.findViewById(R.id.radioButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_gear_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = gearCategories[position]
        holder.radioButton.text = category.categoryName
        holder.radioButton.isChecked = category.gearCategoryId == selectedCategoryId // デフォルト値を設定

        // ラジオボタンのクリックリスナーを設定
        holder.radioButton.setOnClickListener {
            // 選択されたカテゴリIDを更新
            selectedCategoryId = category.gearCategoryId
            // アダプタを更新
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return gearCategories.size
    }

    // 選択されたカテゴリIDを取得するメソッド
    fun getSelectedCategoryId(): Long {
        return selectedCategoryId
    }
}