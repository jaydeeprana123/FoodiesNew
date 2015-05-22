package yalantis.com.sidemenu.foodies.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.sample.R;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class FragmentContactUs extends Fragment implements ScreenShotable {

    public static final String CASE = "Case";


    private View containerView;
    protected ImageView mImageView;
    protected int res;
    private Bitmap bitmap;

    public static FragmentContactUs newInstance() {
        FragmentContactUs contentFragment = new FragmentContactUs();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(),android.R.drawable.screen_background_light_transparent);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(Integer.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact_us, container, false);
        mImageView = (ImageView) rootView.findViewById(R.id.image_content);
        mImageView.setClickable(true);
        mImageView.setFocusable(true);
        mImageView.setImageResource(res);
//        Toast.makeText(getActivity(), "content fragment 3", Toast.LENGTH_LONG).show();
        return rootView;
    }

    @Override
    public void takeScreenShot() {
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
//                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
//                Canvas canvas = new Canvas(bitmap);
//                containerView.draw(canvas);
//                ContentFragment3.this.bitmap = bitmap;
//            }
//        };
//
//        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}

