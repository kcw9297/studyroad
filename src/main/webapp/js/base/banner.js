/**
 * 현재는 홈에만 있는 배너 출력 스크립트
 */

function initBannerSlider(containerSelector, interval = 3000) {
  const slides = document.querySelector(containerSelector);
  const slideImages = slides.querySelectorAll('img');
  const slideCount = slideImages.length;

  if (slideCount < 2) return;

  let currentIndex = 0;
  let isMoving = false;

  // 초기 위치
  slides.style.transform = `translateX(${-currentIndex * 100}%)`;

  function showSlide(index) {
    isMoving = true;
    slides.style.transition = 'transform 0.6s ease';
    slides.style.transform = `translateX(${-index * 100}%)`;

    slides.addEventListener('transitionend', function handler() {
      isMoving = false;
      slides.removeEventListener('transitionend', handler);
    });
  }

  function prevBanner() {
    if (!isMoving) {
      currentIndex = (currentIndex - 1 + slideCount) % slideCount;
      showSlide(currentIndex);
    }
  }

  function nextBanner() {
    if (!isMoving) {
      currentIndex = (currentIndex + 1) % slideCount;
      showSlide(currentIndex);
    }
  }

  const timer = setInterval(nextBanner, interval);

  return {
    prev: prevBanner,
    next: nextBanner,
    stop: () => clearInterval(timer)
  };
}