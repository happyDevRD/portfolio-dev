-- Skill Quarkus (BD ya sembrada sin DataLoader)
INSERT INTO skills (name, proficiency, icon_url, category)
SELECT 'Quarkus', 88, NULL, 'Backend'
WHERE NOT EXISTS (SELECT 1 FROM skills WHERE name = 'Quarkus');
