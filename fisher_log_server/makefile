CAKE=$(shell cat CAKE)
GIT=$(shell cat GIT)
SERVICE=$(shell cat SERVICE)
PLUGIN=$(shell cat CAKE)/app/Plugin
VENDOR=$(shell cat CAKE)/app/Vendor
CONFIG=$(shell cat CAKE)/app/Config
LN=
ifeq ($(shell uname), Linux)
	LN=ln -sTf
else
	LN=ln -sf
endif

all:
	@echo Main module
	@echo CAKE: $(CAKE)
	@echo GIT: $(GIT)
	@echo SERVICE: $(SERVICE)
	@echo install
	@echo
	@make all-git
	@echo
	@make all-security

include security.mk

include git.mk

install: $(PLUGIN)/Main $(VENDOR)/Google $(CONFIG)/core.php $(CONFIG)/bootstrap.php $(CONFIG)/database.php $(CONFIG)/routes.php
	@echo finished.

$(PLUGIN)/Main: $(PLUGIN)/Security
	cd $(PLUGIN) && $(LN) $(realpath $(PWD)/) $(shell basename $@) && touch $(shell basename $@)

$(VENDOR)/Google: $(GIT)/google-api-php-client
	cd $(VENDOR) && $(LN) $(realpath $<)/src/Google $(shell basename $@) && touch $(shell basename $@)

$(CONFIG)/core.php: Config/$(SERVICE)/core.php
	cp $< $@

$(CONFIG)/bootstrap.php: Config/$(SERVICE)/bootstrap.php
	cp $< $@

$(CONFIG)/database.php: Config/$(SERVICE)/database.php
	cp $< $@

$(CONFIG)/routes.php: Config/$(SERVICE)/routes.php
	cp $< $@

$(PLUGIN)/Security: $(PLUGIN)/Style
	make $(GIT)/cp_security
	cd $(PLUGIN) && $(LN) $(GIT)/cp_security/ $(shell basename $@) && touch $(shell basename $@)

$(PLUGIN)/Style: $(PLUGIN)/DebugKit
	make $(GIT)/cp_style
	cd $(PLUGIN) && $(LN) $(GIT)/cp_style/ $(shell basename $@) && touch $(shell basename $@)

$(PLUGIN)/DebugKit: $(CAKE)
	make $(GIT)/debug_kit
	cd $(PLUGIN) && $(LN) $(GIT)/debug_kit/ $(shell basename $@) && touch $(shell basename $@)

$(CAKE):
	git clone https://github.com/cakephp/cakephp.git $@
	cd $@ && git checkout tags/2.8.5
