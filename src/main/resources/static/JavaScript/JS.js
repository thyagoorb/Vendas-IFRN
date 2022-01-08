
/* Efeito de rolagem */

var btnSignin = document.querySelector("#signin");
var btnSignup = document.querySelector("#signup");

var body = document.querySelector("body");


btnSignin.addEventListener("click", function () {
   body.className = "sign-in-js"; 
});

btnSignup.addEventListener("click", function () {
    body.className = "sign-up-js";
})

/* Efeito de rolagem */


/* Efeito do Navbar */

class MobileNavbar {
	constructor(mobileMenu, navList, navLinks) {
	  this.mobileMenu = document.querySelector(mobileMenu);
	  this.navList = document.querySelector(navList);
	  this.navLinks = document.querySelectorAll(navLinks);
	  this.activeClass = "active";
  
	  this.handleClick = this.handleClick.bind(this);
	}
  
	animateLinks() {
	  this.navLinks.forEach((link, index) => {
		link.style.animation
		  ? (link.style.animation = "")
		  : (link.style.animation = `navLinkFade 0.5s ease forwards ${
			  index / 7 + 0.3
			}s`);
	  });
	}
  
	handleClick() {
	  this.navList.classList.toggle(this.activeClass);
	  this.mobileMenu.classList.toggle(this.activeClass);
	  this.animateLinks();
	}
  
	addClickEvent() {
	  this.mobileMenu.addEventListener("click", this.handleClick);
	}
  
	init() {
	  if (this.mobileMenu) {
		this.addClickEvent();
	  }
	  return this;
	}
  }
  
  const mobileNavbar = new MobileNavbar(
	".mobile-menu",
	".nav-list",
	".nav-list li",
  );
  mobileNavbar.init();

  /* Efeito do Navbar */
  
