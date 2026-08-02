document.addEventListener("DOMContentLoaded", function () {
  // 1. Dynamic Year in Footer
  const yearElement = document.getElementById("year");
  if (yearElement) {
    yearElement.textContent = new Date().getFullYear();
  }

  // 2. Form Submission Alert
  const contactForm = document.getElementById("contactForm");
  if (contactForm) {
    contactForm.addEventListener("submit", function (e) {
      e.preventDefault();
      const name = document.getElementById("name").value;
      alert("Thank you " + name + "! Your message has been sent successfully.");
      contactForm.reset();
    });
  }

  // 3. Floating Butterfly Effect
  function createButterfly() {
    const container = document.getElementById("butterfly-container");
    if (!container) return;

    const butterfly = document.createElement("div");
    butterfly.classList.add("butterfly");

    // Random starting horizontal position
    butterfly.style.left = Math.random() * 90 + "vw";

    // Random speed and size
    const duration = Math.random() * 4 + 5; // 5s - 9s
    const size = Math.random() * 15 + 20; // 20px - 35px

    butterfly.style.animationDuration = duration + "s";
    butterfly.style.fontSize = size + "px";
    butterfly.innerHTML = "🦋";

    container.appendChild(butterfly);

    // Remove butterfly after animation ends
    setTimeout(() => {
      butterfly.remove();
    }, duration * 1000);
  }

  // Initial butterflies start immediately
  createButterfly();
  
  // Generate a new butterfly every 1 second
  setInterval(createButterfly, 1000);
});
