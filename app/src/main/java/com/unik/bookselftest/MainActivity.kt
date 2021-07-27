package com.unik.bookselftest

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.unik.bookselftest.adapters.BookSelfAdapter
import com.unik.bookselftest.adapters.CategoriesAdapter
import com.unik.bookselftest.model.BooksModel
import com.unik.bookselftest.model.Categories
import com.unik.bookselftest.utilities.OnItemClickListener
import com.unik.bookselftest.utilities.PopUtils
import com.unik.bookselftest.utilities.ReturnValue
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var ivNavigationMenu: ImageView
    lateinit var txtAddCategory: TextView
    lateinit var txtTitle: TextView
    lateinit var ivAddBook: ImageView
    lateinit var drawerLayout: DrawerLayout
    lateinit var rvBookSelf: RecyclerView
    lateinit var rvCategories: RecyclerView

    lateinit var categoriesArrayList: ArrayList<Categories>

    lateinit var category: Categories

    companion object {
        lateinit var booksArrayList: ArrayList<BooksModel>
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initUI() {
        drawerLayout = findViewById(R.id.drawer_layout)
        ivAddBook = findViewById(R.id.ivAddBook)
        rvBookSelf = findViewById(R.id.rvBookSelf)
        rvCategories = findViewById(R.id.rvCategories)
        ivNavigationMenu = findViewById(R.id.ivNavigationMenu)
        txtAddCategory = findViewById(R.id.txtAddCategory)
        txtTitle = findViewById(R.id.txtTitle)

        drawerOpenClose()
        booksArrayList = ArrayList()
        category = Categories("1", "Default", 0)
        categoriesArrayList = ArrayList()
        categoriesArrayList.add(category)

        rvBookSelf.layoutManager = GridLayoutManager(this, 3)
        rvBookSelf.setHasFixedSize(true)

        rvCategories.layoutManager = LinearLayoutManager(this)
        setCategoryAdapter()
        ivNavigationMenu.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        txtAddCategory.setOnClickListener {
            popUpCategoryDialog()
        }
        ivAddBook.setOnClickListener {
            callBottomSheetAddBook()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun callBottomSheetAddBook() {
        val dialog = Dialog(this, R.style.AlertDialogCustom)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val v = LayoutInflater.from(this).inflate(R.layout.dialog_add_books, null)

        val edtBookTitle: EditText = v.findViewById(R.id.edtBookTitle)
        val txtSubmit: TextView = v.findViewById(R.id.txtSubmit)
        val ivBook: ImageView = v.findViewById(R.id.ivBook)

        dialog.window!!.attributes.windowAnimations = R.style.AlertDialogCustom
        val lp = WindowManager.LayoutParams()
        val window = dialog.window
        lp.copyFrom(window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        val randomImageInt = (1..6).random()

        when (randomImageInt) {
            1 -> ivBook.setImageResource(R.drawable.self_one)
            2 -> ivBook.setImageResource(R.drawable.self_two)
            3 -> ivBook.setImageResource(R.drawable.self_three)
            4 -> ivBook.setImageResource(R.drawable.self_four)
            5 -> ivBook.setImageResource(R.drawable.self_five)
            6 -> ivBook.setImageResource(R.drawable.self_six)
        }
        txtSubmit.setOnClickListener {
            if (!edtBookTitle.text.toString().trim().isEmpty()) {
                val bookModel = BooksModel(
                    category.id,
                    edtBookTitle.text.toString().trim(), LocalDateTime.now().toString(),
                    randomImageInt
                )
                booksArrayList.add(bookModel)

                val booksArrayListNew = ArrayList<BooksModel>()
                if (booksArrayList.size != 0) {
                    for (i in 0 until booksArrayList.size) {
                        if (booksArrayList.get(i).categoryId.equals(category.id)) {
                            booksArrayListNew.add(booksArrayList.get(i))
                        }
                    }
                    txtTitle.text = category.categoryTitle
                    rvBookSelf.adapter = BookSelfAdapter(this, booksArrayListNew)
                }

                dialog.dismiss()
            } else {
                Toast.makeText(this, "Enter Book Title", Toast.LENGTH_LONG).show()

            }
        }

//        val bottomSheet = BottomSheetAddBookDialog(category)
//        bottomSheet.show(supportFragmentManager, "ModalBottomSheet")
        dialog.setContentView(v)
        dialog.setCancelable(true)

        val width = (this.getResources().getDisplayMetrics().widthPixels * 0.90).toInt()
        val height = (this.getResources().getDisplayMetrics().heightPixels * 0.30).toInt()
        dialog.window!!.setLayout(width, lp.height)

        dialog.show()
    }

    private fun popUpCategoryDialog() {
        val dialog = Dialog(this, R.style.AlertDialogCustom)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val v = LayoutInflater.from(this).inflate(R.layout.category_dialog, null)
        val edtCategory: EditText = v.findViewById(R.id.edtCategory)
        val txtSubmit: TextView = v.findViewById(R.id.txtSubmit)

        dialog.window!!.attributes.windowAnimations = R.style.AlertDialogCustom
        val lp = WindowManager.LayoutParams()
        val window = dialog.window
        lp.copyFrom(window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        txtSubmit.setOnClickListener {
            if (!edtCategory.text.trim().toString().isEmpty()) {
                categoriesArrayList.add(
                    Categories(
                        "" + (categoriesArrayList.size + 1),
                        "" + edtCategory.text.toString().trim(), 0
                    )
                )
                setCategoryAdapter()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Please Enter Category", Toast.LENGTH_LONG).show()
            }
        }

        dialog.setContentView(v)
        dialog.setCancelable(true)

        val width = (this.getResources().getDisplayMetrics().widthPixels * 0.90).toInt()
        val height = (this.getResources().getDisplayMetrics().heightPixels * 0.30).toInt()
        dialog.window!!.setLayout(width, lp.height)

        dialog.show()
    }

    private fun setCategoryAdapter() {
        rvCategories.adapter = CategoriesAdapter(this, categoriesArrayList,
            OnItemClickListener { categories: Categories, i: Int ->
                drawerLayout.closeDrawer(GravityCompat.START)

                category = categories

                txtTitle.text = categories.categoryTitle


                val booksArrayListNew = ArrayList<BooksModel>()
                if (booksArrayList.size != 0) {
                    for (i in 0 until booksArrayList.size) {
                        if (booksArrayList.get(i).categoryId.equals(categories.id)) {
                            booksArrayListNew.add(booksArrayList.get(i))
                        }
                    }
                    rvBookSelf.adapter = BookSelfAdapter(this, booksArrayListNew)
                } else {
                    Toast.makeText(this, "No Books Added in this Category", Toast.LENGTH_LONG)
                        .show()
                }
                for (j in 0 until categoriesArrayList.size) {
                    if (categoriesArrayList.get(j).id.equals(categories.id)) {

                        categoriesArrayList.get(i).selected = 1
                    }else{

                        categoriesArrayList.get(i).selected = 0
                    }
                }

            })
    }

    private fun drawerOpenClose() {
        // Close the soft keyboard when you open or close the Drawer
        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            null,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerClosed(drawerView: View) {
                // Triggered once the drawer closes
                super.onDrawerClosed(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

            override fun onDrawerOpened(drawerView: View) {
                // Triggered once the drawer opens
                super.onDrawerOpened(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }
        }
        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()
    }

    class BottomSheetAddBookDialog(val category: Categories) :
        BottomSheetDialogFragment() {

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

            val view =
                LayoutInflater.from(context).inflate(R.layout.dialog_add_books, container, false)

            val edtBookTitle: EditText = view.findViewById(R.id.edtBookTitle)
//            val edtCategory: EditText = view.findViewById(R.id.edtCategory)
            val txtSubmit: TextView = view.findViewById(R.id.txtSubmit)
            val ivBook: ImageView = view.findViewById(R.id.ivBook)

            var position: Int = 0

            val randomImageInt = (1..6).random()

            when (randomImageInt) {
                1 -> ivBook.setImageResource(R.drawable.self_one)
                2 -> ivBook.setImageResource(R.drawable.self_two)
                3 -> ivBook.setImageResource(R.drawable.self_three)
                4 -> ivBook.setImageResource(R.drawable.self_four)
                5 -> ivBook.setImageResource(R.drawable.self_five)
                6 -> ivBook.setImageResource(R.drawable.self_six)
            }
           /* edtCategory.setOnClickListener {
                PopUtils.dialogListShowEditText(context, categoriesArrayList, "Select Category",
                    edtCategory, ReturnValue { value, positionValue ->
                        run {
                            position = positionValue
                        }
                    })
            }*/
            txtSubmit.setOnClickListener {
                if (!edtBookTitle.text.toString().trim().isEmpty()) {
                    val bookModel = BooksModel(
                        category.id,
                        edtBookTitle.text.toString().trim(), LocalDateTime.now().toString(),
                        randomImageInt
                    )
                    booksArrayList.add(bookModel)

                    dismiss()
                } else {
                    Toast.makeText(context, "Enter Book Title", Toast.LENGTH_LONG).show()

                }
            }
            return view
        }

    }


}