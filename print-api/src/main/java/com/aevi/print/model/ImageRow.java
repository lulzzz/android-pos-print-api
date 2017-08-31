/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aevi.print.model;

import android.graphics.Bitmap;

import com.aevi.android.rxmessenger.JsonConverter;


/**
 * This class represents an image line in a {@link PrintPayload}.
 */
public class ImageRow implements PrintRow {

    public static final int DEFAULT_CONTRAST_LEVEL = 50;

    private Bitmap image;
    private Alignment alignment = Alignment.LEFT;
    private int contrastLevel = DEFAULT_CONTRAST_LEVEL;

    /**
     * Creates a left aligned image row at normal contrast (50) with the given
     * bitmap.
     *
     * @param image the image to print. This parameter must not be null.
     */
    public ImageRow(Bitmap image) {
        if (image == null) {
            throw new IllegalArgumentException("image must not be null");
        }

        this.image = image;
    }

    /**
     * Gets the image of this image row.
     *
     * @return the image.
     */
    public Bitmap getImage() {
        return image;
    }

    /**
     * Gets the alignment of this image row.
     *
     * @return the alignment,
     */
    public Alignment getAlignmentStyle() {
        return alignment;
    }

    /**
     * Sets the alignment of this image row.
     *
     * @param alignment the alignment to set,
     * @return This ImageRow object
     */
    public ImageRow align(Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    /**
     * Gets the contrast level of this image row. The contrast level is a
     * percentage value between 0 and 100, where 100 is full contrast.
     *
     * @return the contrastLevel to set,
     */
    public int getContrastLevel() {
        return contrastLevel;
    }

    /**
     * Sets the contrast level of this image row. The contrast level is a
     * value between 0 and 100, where 0 is full white and 100 is full black. If a
     * value lower then 0 of higher then 100 is given an
     * {@link IllegalArgumentException} is thrown.
     *
     * @param contrastLevel the contrastLevel to set
     * @return This ImageRow object
     */
    public ImageRow contrastLevel(int contrastLevel) {
        if (contrastLevel < 0 || contrastLevel > 100) {
            throw new IllegalArgumentException("contrastLevel must be between 0 and 100");
        }

        this.contrastLevel = contrastLevel;
        return this;
    }

    @Override
    public String toJson() {
        return JsonConverter.serialize(this);
    }

    public static ImageRow fromJson(String json) {
        return JsonConverter.deserialize(json, ImageRow.class);
    }
}