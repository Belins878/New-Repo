package com.example.neighbors.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.neighbors.R
import com.example.neighbors.fragments.ListNeighborsFragment
import com.example.neighbors.models.Neighbor


private lateinit var deleteButton: ImageButton

class ListNeighborsAdapter(
    items: MutableList<Neighbor>
) : RecyclerView.Adapter<ListNeighborsAdapter.ViewHolder>() {
    private val mNeighbours: MutableList<Neighbor> = items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.neighbor_item, parent, false)

        deleteButton = view.findViewById(R.id.item_list_delete_button)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val neighbour: Neighbor = mNeighbours[position]
        // Display Neighbour Name
        holder.mNeighbourName.text = neighbour.name

        val context : Context = holder.mNeighbourAvatar.context
// Display Neighbour Avatar
        Glide.with(context)
            .load(neighbour.avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.ic_baseline_person_24)
            .error(R.drawable.ic_baseline_person_24)
            .skipMemoryCache(false)
            .into(holder.mNeighbourAvatar)

        holder.mDeleteButton.setOnClickListener{
            val alert = AlertDialog.Builder(context)
            // set message of alert dialog
            alert.setMessage(R.string.wantToDeleteNeighbor)
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton(R.string.yes, DialogInterface.OnClickListener { dialog, id ->
                    val listNeighborsFragment = ListNeighborsFragment()
                    listNeighborsFragment.onDeleteNeighbor(neighbour)
                    mNeighbours.remove(neighbour)
                    notifyDataSetChanged()
                })
                // negative button text and action
                .setNegativeButton(R.string.no, DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })

            val alertDialog: AlertDialog = alert.create()
            alertDialog.show()
        }
    }

    override fun getItemCount(): Int {
        return mNeighbours.size
    }

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val mNeighbourAvatar: ImageView = view.findViewById(R.id.item_list_avatar)
        val mNeighbourName: TextView = view.findViewById(R.id.item_list_name)
        val mDeleteButton: ImageButton = view.findViewById(R.id.item_list_delete_button)

        init {
            // Enable click on item
        }
    }

}