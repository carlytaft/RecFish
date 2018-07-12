SUDO=$(shell cat SUDO)
APACHE=$(shell cat APACHE)

all-security:
	@echo Security module
	@echo SUDO: $(SUDO)
	@echo APACHE: $(APACHE)
	@echo open, close

open:
	$(SUDO) chown -R $(APACHE):$(APACHE) $(CAKE)/app/tmp

close:
	$(SUDO) chown -R $(USER) $(CAKE)/app/tmp
