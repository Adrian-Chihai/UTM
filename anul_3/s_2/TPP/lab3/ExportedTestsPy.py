„# Generated by Selenium IDE
import pytest
import time
import json
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.support import expected_conditions
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.desired_capabilities import DesiredCapabilities

class TestTest1():
  def setup_method(self, method):
    self.driver = webdriver.Firefox()
    self.vars = {}
  
  def teardown_method(self, method):
    self.driver.quit()
  
  def test_test1(self):
    self.driver.get("https://www.mortgagecalculator.org/")
    self.driver.set_window_size(1586, 866)
    self.driver.switch_to.frame(0)
    self.driver.execute_script("window.scrollTo(0,0)")
    self.driver.switch_to.default_content()
    element = self.driver.find_element(By.ID, "homeval")
    actions = ActionChains(self.driver)
    actions.move_to_element(element).click_and_hold().perform()
    element = self.driver.find_element(By.ID, "homeval")
    actions = ActionChains(self.driver)
    actions.move_to_element(element).perform()
    element = self.driver.find_element(By.ID, "homeval")
    actions = ActionChains(self.driver)
    actions.move_to_element(element).release().perform()
    self.driver.find_element(By.ID, "homeval").click()
    self.driver.find_element(By.ID, "homeval").send_keys("250000")
    self.driver.find_element(By.ID, "intrstsrate").click()
    self.driver.find_element(By.NAME, "cal").click()
    self.driver.find_element(By.CSS_SELECTOR, ".cal-right-box").click()
    self.driver.find_element(By.CSS_SELECTOR, ".rw-box:nth-child(1) > .left-cell > h3").click()
    self.driver.find_element(By.CSS_SELECTOR, ".cal-right-box").click()
    self.driver.find_element(By.CSS_SELECTOR, ".rw-box:nth-child(1) > .left-cell > h3").click()
    self.driver.find_element(By.CSS_SELECTOR, ".rw-box:nth-child(1) > .left-cell > h3").click()
    self.vars["monthlyPayment"] = self.driver.find_element(By.CSS_SELECTOR, ".rw-box:nth-child(1) > .left-cell > h3").text
    print("999,24")
    print("{}".format(self.vars["monthlyPayment"]))
  