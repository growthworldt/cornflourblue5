/*
 * Copyright 2014 Toxic Bakery
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zftlive.android.sample.animation.viewpager.transfor;
//稍稍点击就切换页面
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

public abstract class ABaseTransformer implements PageTransformer {

	/**
	 * Called each {@link #transformPage(android.view.View, float)}.
	 * 
	 * @param page
	 *            Apply the transformation to this page
	 * @param position
	 *            Position of page relative to the current front-and-center position of the pager. 0 is front and
	 *            center. 1 is one full page position to the right, and -1 is one page position to the left.
	 */
	protected abstract void onTransform(View page, float position);

	/**
	 * Apply a property transformation to the given page. For most use cases, this method should not be overridden.
	 * Instead use {@link #transformPage(android.view.View, float)} to perform typical transformations.
	 * 
	 * @param page
	 *            Apply the transformation to this page
	 * @param position
	 *            Position of page relative to the current front-and-center position of the pager. 0 is front and
	 *            center. 1 is one full page position to the right, and -1 is one page position to the left.
	 */
	@Override
	public void transformPage(View page, float position) {
		onPreTransform(page, position);
		onTransform(page, position);
		onPostTransform(page, position);
	}

	/**
	 * If the position offset of a fragment is less than negative one or greater than one, returning true will set the
	 * fragment alpha to 0f. Otherwise fragment alpha is always defaulted to 1f.
	 * 
	 * @return
	 */
	protected boolean hideOffscreenPages() {
		return true;
	}

	/**
	 * Indicates if the default animations of the view pager should be used.
	 * 
	 * @return
	 */
	protected boolean isPagingEnabled() {
		return false;
	}

	/**
	 * Called each {@link #transformPage(android.view.View, float)} before {{@link #onTransform(android.view.View, float)}.
	 * <p>
	 * The default implementation attempts to reset all view properties. This is useful when toggling transforms that do
	 * not modify the same page properties. For instance changing from a transformation that applies rotation to a
	 * transformation that fades can inadvertently leave a fragment stuck with a rotation or with some degree of applied
	 * alpha.
	 * 
	 * @param page
	 *            Apply the transformation to this page
	 * @param position
	 *            Position of page relative to the current front-and-center position of the pager. 0 is front and
	 *            center. 1 is one full page position to the right, and -1 is one page position to the left.
	 */
	protected void onPreTransform(View page, float position) {
		final float width = page.getWidth();

		ViewHelper.setRotationX(page,0);
		ViewHelper.setRotationX(page,0);
		ViewHelper.setRotationY(page,0);
		ViewHelper.setRotation(page,0);
		ViewHelper.setScaleX(page,0);
		ViewHelper.setScaleY(page,0);
		ViewHelper.setPivotX(page,0);
		ViewHelper.setPivotY(page,0);
		ViewHelper.setTranslationY(page,0);
		ViewHelper.setTranslationX(page,isPagingEnabled() ? 0f : -width * position);

		if (hideOffscreenPages()) {
			ViewHelper.setAlpha(page,position <= -1f || position >= 1f ? 0f : 1f);
			page.setEnabled(false);
		} else {
			page.setEnabled(true);
			ViewHelper.setAlpha(page,1f);
		}
	}

	/**
	 * Called each {@link #transformPage(android.view.View, float)} after {@link #onTransform(android.view.View, float)}.
	 * 
	 * @param page
	 *            Apply the transformation to this page
	 * @param position
	 *            Position of page relative to the current front-and-center position of the pager. 0 is front and
	 *            center. 1 is one full page position to the right, and -1 is one page position to the left.
	 */
	protected void onPostTransform(View page, float position) {
	}

	/**
	 * Same as {@link Math#min(double, double)} without double casting, zero closest to infinity handling, or NaN support.
	 * 
	 * @param val
	 * @param min
	 * @return
	 */
	protected static final float min(float val, float min) {
		return val < min ? min : val;
	}

}
