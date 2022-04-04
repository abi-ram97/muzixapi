import { AppPage } from './app.po';
import { browser, element, by, protractor } from 'protractor';

describe('Muzix App', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(browser.getTitle()).toEqual('MuzixApp');
  });
  it('should show login page on load',()=>{
    expect(browser.driver.getCurrentUrl()).toContain('login');
  });
  it('should redirect to register on click',()=>{
    element(by.css('.register-nav')).click();
    expect(browser.driver.getCurrentUrl()).toContain('register');
  });
  it('should be able to register user',()=>{
    element(by.css('#userId')).sendKeys('testuser');
    element(by.css('#password')).sendKeys('testuser');
    element(by.css('#firstName')).sendKeys('test');
    element(by.css('#lastName')).sendKeys('user');
    element(by.css('.register-user')).click();
    browser.driver.sleep(1500);
    expect(browser.driver.getCurrentUrl()).toContain('login');
  });
  it('should be able to view trending tracks',()=>{
    element(by.css('#userId')).sendKeys('testuser');
    element(by.css('#password')).sendKeys('testuser');
    element(by.css('.login-user')).click();
    browser.driver.sleep(1500);
    expect(browser.driver.getCurrentUrl()).toContain('muzix/top-tracks');
  });
  it('should be able to view popular tracks and add to playlist',()=>{
    element(by.css('.popular-nav')).click();
    browser.driver.sleep(2000);
    const tracks = element.all(by.css('.track'));
    expect(tracks.isDisplayed()).toBeTruthy();
    const track = tracks.get(0);
    track.element(by.css('button')).click();
  });
  it('should be able to search for tracks',()=>{
    element(by.css('.search-nav')).click();
    expect(browser.driver.getCurrentUrl()).toContain('muzix/search');
    element(by.css('.search-input-button')).sendKeys('believer');
    element(by.css('.search-input-button')).sendKeys(protractor.Key.ENTER);
    const tracks = element.all(by.css('.track'));
    expect(tracks.isDisplayed()).toBeTruthy();
  });
  it('should be able to view playlist and delete',()=>{
    element(by.css('.playlist-nav')).click();
    expect(browser.driver.getCurrentUrl()).toContain('muzix/favorites');
    browser.driver.sleep(2000);
    const tracks = element.all(by.css('.track'));
    expect(tracks.isDisplayed()).toBeTruthy();
    const track = tracks.get(0);
    track.element(by.css('button')).click();
  });
  it('should be able to logout',()=>{
    element(by.css('.logout-button')).click();
    expect(browser.driver.getCurrentUrl()).toContain('login');
  });
});
