
document.addEventListener("DOMContentLoaded", function() {
	const slides = document.querySelector('.slides');
	const slideImages = document.querySelectorAll('.slides img');
	const slideCount = slideImages.length;
	let currentIndex = 1;
	let isMoving = false;
	
	slides.style.transform = `translateX(${-currentIndex * 100}%)`;
	
	function showSlide(index) {
	  isMoving = true;
	  slides.style.transition = 'transform 0.6s ease';
	  slides.style.transform = `translateX(${-index * 100}%)`;
	
	  slides.addEventListener('transitionend', function handler() {
	    isMoving = false;
	    if (index === 0) {
	      slides.style.transition = 'none';
	      currentIndex = slideCount - 2;
	      slides.style.transform = `translateX(${-currentIndex * 100}%)`;
	    } else if (index === slideCount - 1) {
	      slides.style.transition = 'none';
	      currentIndex = 1;
	      slides.style.transform = `translateX(${-currentIndex * 100}%)`;
	    }
	    slides.removeEventListener('transitionend', handler);
	  });
	}
	
	function prevBanner() {
	  if (!isMoving) {
	    currentIndex--;
	    showSlide(currentIndex);
	  }
	}
	
	function nextBanner() {
	  if (!isMoving) {
	    currentIndex++;
	    showSlide(currentIndex);
	  }
	}
	
	document.querySelector(".prev").addEventListener("click", prevBanner);
	document.querySelector(".next").addEventListener("click", nextBanner);
	
	setInterval(nextBanner, 3000);
});