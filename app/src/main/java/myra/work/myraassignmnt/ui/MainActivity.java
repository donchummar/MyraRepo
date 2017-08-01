package myra.work.myraassignmnt.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.error.VolleyError;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import myra.work.myraassignmnt.R;
import myra.work.myraassignmnt.databinding.ActivityMainBinding;
import myra.work.myraassignmnt.model.OrderRequest;
import myra.work.myraassignmnt.net.IDataModel;
import myra.work.myraassignmnt.utils.GetImageThumbnail;

public class MainActivity extends BaseActivity {


    private ActivityMainBinding binding;
    private static String root = null;
    private static String imageFolderPath = null;
    private String imageName = null;
    private static Uri fileUri = null;
    private static final int CAMERA_IMAGE_REQUEST = 1;
    private Map<Integer, OrderRequest> tags;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Myra");
        tags = new HashMap<>();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceedCapture();
            }
        });
        binding.overlayLyt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                inflateViews(view, motionEvent);
                System.out.println(" X & Y" + motionEvent.getX() + "      " + motionEvent.getY());
                return false;
            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOrder();
            }
        });
    }

    private void sendOrder() {
        if (tags != null && tags.size() > 0 ) {
            dataLoader.postCandidateInformation(this, tags, bitmap);
        }
    }

    private void inflateViews(View view, MotionEvent event) {
        final RelativeLayout childView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.inflating_view, null);
        int id = View.generateViewId();
        Button icon = (Button) childView.findViewById(R.id.icon_overlay);
        final LinearLayout childlyt = (LinearLayout) childView.findViewById(R.id.child_lyt);
        final TextView removeChild = (TextView) childView.findViewById(R.id.remove_child_text);
        final EditText textQty = (EditText) childView.findViewById(R.id.edit_qty);
        textQty.addTextChangedListener(new ViewTextWatcher(textQty));
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (childlyt.getVisibility() == View.GONE) {
                    childlyt.setVisibility(View.VISIBLE);
                } else {
                    childlyt.setVisibility(View.GONE);
                }
            }
        });
        removeChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.overlayLyt.removeView(childView);
                removeFromTags(((View) childlyt.getParent()).getId());
            }
        });
        if (event.getAction() == MotionEvent.ACTION_UP) {
            childView.setX(event.getX());
            childView.setY(event.getY());

            childView.setId(id);
            binding.overlayLyt.addView(childView);
            addCordinates(event, id);
        }
    }

    private void removeFromTags(int id) {
        if (tags != null && tags.containsKey(id)) {
            tags.remove(id);
        }
    }

    private void addCordinates(MotionEvent event, int id) {
        OrderRequest orderRequest = new OrderRequest();
        int xValue = (int) event.getX();
        int yValue = (int) event.getY();
        orderRequest.setxCordinate(xValue);
        orderRequest.setyCordinate(yValue);
        tags.put(id, orderRequest);
    }

    private void proceedCapture() {
        // fetching the root directory
        root = Environment.getExternalStorageDirectory().toString()
                + "/Your_Folder";
        // Creating folders for Image
        imageFolderPath = root + "/saved_images";
        File imagesFolder = new File(imageFolderPath);
        imagesFolder.mkdirs();

        // Generating file name
        imageName = "test.png";

        // Creating image here
        File image = new File(imageFolderPath, imageName);
        fileUri = Uri.fromFile(image);
        binding.capturedPhoto.setTag(imageFolderPath + File.separator + imageName);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(takePictureIntent,
                CAMERA_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_IMAGE_REQUEST:
                    try {
                        GetImageThumbnail getImageThumbnail = new GetImageThumbnail();
                        bitmap = getImageThumbnail.getThumbnail(fileUri, this);
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    // Setting image image icon on the imageview
                    binding.capturedPhoto.setImageBitmap(bitmap);
                    binding.overlayLyt.setVisibility(View.VISIBLE);
                    break;
                default:
                    Toast.makeText(this, "Something went wrong...",
                            Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

    class ViewTextWatcher implements TextWatcher {
        private EditText editText;

        public ViewTextWatcher(EditText editText) {
        this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            updateQtyOnTags(editable.toString(), ((View) editText.getParent().getParent()).getId());
        }
    }

    private void updateQtyOnTags(String text, int id) {
      if (tags != null && tags.containsKey(id)){
          OrderRequest orderDetails = tags.get(id);
          orderDetails.setTextOrder(text);
          tags.remove(id);
          tags.put(id, orderDetails);
      }

    }

    @Override
    public void onDataLoadSuccess(IDataModel dataModel) {
        super.onDataLoadSuccess(dataModel);
        Toast.makeText(this, "Order recieved", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDataLoadFailure(VolleyError volleyError) {
        super.onDataLoadFailure(volleyError);
    }
}
