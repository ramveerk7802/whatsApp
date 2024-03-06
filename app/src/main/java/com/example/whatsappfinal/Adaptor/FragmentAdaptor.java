package com.example.whatsappfinal.Adaptor;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.whatsappfinal.Fragment.CallFragment;
import com.example.whatsappfinal.Fragment.ChatFragment;
import com.example.whatsappfinal.Fragment.StatusFragment;

public class FragmentAdaptor extends FragmentStateAdapter {

    public FragmentAdaptor(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0)
            return new ChatFragment();
        else if(position==1)
            return new StatusFragment();
        else if (position==2) {
            return  new CallFragment();
        }
        else
            return new ChatFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
