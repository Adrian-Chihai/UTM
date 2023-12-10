import numpy as np
import matplotlib.pyplot as plt
from skimage import io, color, util, restoration
from scipy import ndimage
from scipy.ndimage import gaussian_filter, median_filter

# Download an example image (replace with your own image if needed)
image_url = "https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.pixelstalk.net%2Fwp-content%2Fuploads%2F2016%2F07%2FLiverpool-HD-Picture.jpg&f=1&nofb=1&ipt=8b74c59ece6d23db798e3beb0bc6a79c33f90b6df005db0588fbf46e7c8b07e7&ipo=images"
image = io.imread(image_url)

# Rotate the image
image_rotated = ndimage.rotate(image, angle=45, reshape=False)

# Resize the image
image_resized = ndimage.zoom(image, zoom=(0.5, 0.5, 1))

# Generate noise on the image
image_noisy = util.random_noise(image, mode='s&p', rng=None)

# Apply the Gaussian filter
image_gaussian = gaussian_filter(image_noisy, sigma=1)

# Apply the Median filter
image_median = median_filter(image_noisy, size=3)

# Convert the image to grayscale
image_gray = color.rgb2gray(image_noisy)

# Create a Gaussian PSF with the same number of channels as the input image
psf = restoration.estimate_sigma(image_gray)


# Display the images
plt.figure(figsize=(12, 12))

plt.subplot(3, 3, 1)
plt.imshow(image)
plt.title('Original Image')

plt.subplot(3, 3, 2)
plt.imshow(image_rotated)
plt.title('Rotated Image')

plt.subplot(3, 3, 3)
plt.imshow(image_resized)
plt.title('Resized Image')

plt.subplot(3, 3, 4)
plt.imshow(image_noisy)
plt.title('Noisy Image')

plt.subplot(3, 3, 5)
plt.imshow(image_gaussian)
plt.title('Gaussian Filter')

plt.subplot(3, 3, 6)
plt.imshow(image_median)
plt.title('Median Filter')


plt.tight_layout()
plt.show()
