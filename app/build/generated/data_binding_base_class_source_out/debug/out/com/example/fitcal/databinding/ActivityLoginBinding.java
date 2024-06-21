// Generated by view binder compiler. Do not edit!
package com.example.fitcal.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.fitcal.R;
import com.example.fitcal.ui.login.CustomEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLoginBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final CustomEditText emailEditText;

  @NonNull
  public final TextInputLayout emailEditTextLayout;

  @NonNull
  public final TextView emailTextView;

  @NonNull
  public final Guideline guidelineHorizontal;

  @NonNull
  public final Guideline guidelineHorizontal2;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final ImageView imageView4;

  @NonNull
  public final ProgressBar loadingProgressBar;

  @NonNull
  public final Button loginButton;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final TextView messageTextView;

  @NonNull
  public final CustomEditText passwordEditText;

  @NonNull
  public final TextInputLayout passwordEditTextLayout;

  @NonNull
  public final TextView passwordTextView;

  @NonNull
  public final TextView titleTextView;

  private ActivityLoginBinding(@NonNull ConstraintLayout rootView,
      @NonNull CustomEditText emailEditText, @NonNull TextInputLayout emailEditTextLayout,
      @NonNull TextView emailTextView, @NonNull Guideline guidelineHorizontal,
      @NonNull Guideline guidelineHorizontal2, @NonNull ImageView imageView,
      @NonNull ImageView imageView4, @NonNull ProgressBar loadingProgressBar,
      @NonNull Button loginButton, @NonNull ConstraintLayout main,
      @NonNull TextView messageTextView, @NonNull CustomEditText passwordEditText,
      @NonNull TextInputLayout passwordEditTextLayout, @NonNull TextView passwordTextView,
      @NonNull TextView titleTextView) {
    this.rootView = rootView;
    this.emailEditText = emailEditText;
    this.emailEditTextLayout = emailEditTextLayout;
    this.emailTextView = emailTextView;
    this.guidelineHorizontal = guidelineHorizontal;
    this.guidelineHorizontal2 = guidelineHorizontal2;
    this.imageView = imageView;
    this.imageView4 = imageView4;
    this.loadingProgressBar = loadingProgressBar;
    this.loginButton = loginButton;
    this.main = main;
    this.messageTextView = messageTextView;
    this.passwordEditText = passwordEditText;
    this.passwordEditTextLayout = passwordEditTextLayout;
    this.passwordTextView = passwordTextView;
    this.titleTextView = titleTextView;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.emailEditText;
      CustomEditText emailEditText = ViewBindings.findChildViewById(rootView, id);
      if (emailEditText == null) {
        break missingId;
      }

      id = R.id.emailEditTextLayout;
      TextInputLayout emailEditTextLayout = ViewBindings.findChildViewById(rootView, id);
      if (emailEditTextLayout == null) {
        break missingId;
      }

      id = R.id.emailTextView;
      TextView emailTextView = ViewBindings.findChildViewById(rootView, id);
      if (emailTextView == null) {
        break missingId;
      }

      id = R.id.guidelineHorizontal;
      Guideline guidelineHorizontal = ViewBindings.findChildViewById(rootView, id);
      if (guidelineHorizontal == null) {
        break missingId;
      }

      id = R.id.guidelineHorizontal2;
      Guideline guidelineHorizontal2 = ViewBindings.findChildViewById(rootView, id);
      if (guidelineHorizontal2 == null) {
        break missingId;
      }

      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.imageView4;
      ImageView imageView4 = ViewBindings.findChildViewById(rootView, id);
      if (imageView4 == null) {
        break missingId;
      }

      id = R.id.loadingProgressBar;
      ProgressBar loadingProgressBar = ViewBindings.findChildViewById(rootView, id);
      if (loadingProgressBar == null) {
        break missingId;
      }

      id = R.id.loginButton;
      Button loginButton = ViewBindings.findChildViewById(rootView, id);
      if (loginButton == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.messageTextView;
      TextView messageTextView = ViewBindings.findChildViewById(rootView, id);
      if (messageTextView == null) {
        break missingId;
      }

      id = R.id.passwordEditText;
      CustomEditText passwordEditText = ViewBindings.findChildViewById(rootView, id);
      if (passwordEditText == null) {
        break missingId;
      }

      id = R.id.passwordEditTextLayout;
      TextInputLayout passwordEditTextLayout = ViewBindings.findChildViewById(rootView, id);
      if (passwordEditTextLayout == null) {
        break missingId;
      }

      id = R.id.passwordTextView;
      TextView passwordTextView = ViewBindings.findChildViewById(rootView, id);
      if (passwordTextView == null) {
        break missingId;
      }

      id = R.id.titleTextView;
      TextView titleTextView = ViewBindings.findChildViewById(rootView, id);
      if (titleTextView == null) {
        break missingId;
      }

      return new ActivityLoginBinding((ConstraintLayout) rootView, emailEditText,
          emailEditTextLayout, emailTextView, guidelineHorizontal, guidelineHorizontal2, imageView,
          imageView4, loadingProgressBar, loginButton, main, messageTextView, passwordEditText,
          passwordEditTextLayout, passwordTextView, titleTextView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
